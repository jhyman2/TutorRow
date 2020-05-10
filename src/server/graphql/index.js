import { ApolloServer, UserInputError } from 'apollo-server';
import log from 'loglevel';
import typeDefs from './typeDefs';

class GraphQL {
  constructor (client) {
    const resolvers = {
      Query: {
        course: async (obj, args) => {
          try {
            const results = await client.query(`SELECT * FROM courses WHERE id=$1`, [args.id]);
            return results.rows[0];
          } catch (e) {
            log.debug('Error resolving course', e);
          }
        },
        student: async (obj, args) => {
          try {
            const results = await client.query(`SELECT * FROM users WHERE id=$1`, [args.id]);
            return results.rows[0];
          } catch (e) {
            log.debug('Error resolving student', e);
          }
        },
        university: async (obj, args) => {
          try {
            const results = await client.query('SELECT * FROM universities WHERE id=$1', [args.id]);
            return results.rows[0];
          } catch (e) {
            log.debug('Error resolving university', e);
          }
        },
        universities: async (obj, args) => {
          try {
            const results = await client.query(`SELECT * FROM universities`);
            return results.rows;
          } catch (e) {
            log.debug(`Error resolving universities`, e);
          }
        }
      },
      Course: {
        students: async (course) => {
          try {
            const results = await client.query(`
              SELECT *
              FROM 
                (SELECT a.student_id
                FROM course_instances a
                WHERE course_id = $1 AND role = $2) c
              
              LEFT JOIN users b
                ON c.student_id = b.id
            `, [course.id, 'student']);
            return results.rows;
          } catch (e) {
            log.debug('Error resolving students', e);
          }
        },
        tutors: async (course) => {
          try {
            const results = await client.query(`
              SELECT *
              FROM 
                (SELECT a.student_id
                FROM course_instances a
                WHERE course_id = $1 AND role = $2) c
              
              LEFT JOIN users b
                ON c.student_id = b.id
            `, [course.id, 'tutor']);
            return results.rows;
          } catch (e) {
            log.debug('Error resolving tutors', e);
          }
        },
      },
      Student: {
        courses: async (student) => {
          try {
            const results = await client.query(
              `
                SELECT *
                  FROM course_instances
                  JOIN courses
                    ON course_instances.course_id=courses.id
                  WHERE student_id=$1
              `,
              [student.id],
            );
            return results.rows;
          }
          catch (e) {
            log.debug('Error resolving courses', e);
          }
        },
        university: async (student) => {
          try {
            const results = await client.query(
              `
                SELECT *
                  FROM universities
                  WHERE id=$1
              `,
              [student.university_id],
            );
            return results.rows[0];
          } catch (e) {
            log.debug('Error resolving university', e);
          }
        },
      },
      University: {
        courses: async (university) => {
          try {
            const results = await client.query(`
              SELECT *
                FROM courses WHERE university_id=$1;`, [university.id]);
            return results.rows;
          } catch (e) {
            log.debug('Error resolving courses', e);
          }
        },
      },
      Mutation: {
        enrollStudentInUniversity: async (obj, args, context) => {
          try {
            const result = await client.query(`
              UPDATE
                users
                SET university_id=$1
                  WHERE id=$2
              `,
              [args.id, context.user.id]
            );
            if (!result.rowCount) {
              throw new UserInputError('Enrolling unsuccessful');
            }

            const users = await client.query(`
              SELECT a.id, a.university_id, a.full_name, b.name as university_name, b.id as university_id
                FROM users a
                  INNER JOIN universities b on a.university_id = b.id
                  WHERE a.id=$1;
              `,
              [context.user.id],
            );
            const [user] = users.rows;
            return {
              ...user,
              university: {
                id: user.university_id,
                name: user.university_name,
              },
            };
          } catch (e) {
            log.debug('Error enrolling in university', e);
          }
        },
        dropCourse: async (obj, args, context) => {
          try {
            const result = await client.query(`
              DELETE
                FROM course_instances
                WHERE student_id=$1
                  AND course_id=$2
              `,
              [context.user.id, args.id]
            );
            if (!result.rowCount) {
              throw new UserInputError('User was not signed up for this course');
            }
            const courses = await client.query(`
              SELECT *
                FROM courses
                WHERE id=$1
              `,
              [args.id],
            );
            return courses.rows[0];
          } catch (e) {
            log.debug('Error dropping course', e);
          }
        },
        signupForCourse: async (obj, args, context) => {
          try {
            const isUserAlreadySignedUp = await client.query(
              `
                SELECT *
                  FROM course_instances
                  WHERE student_id=$1
                    AND course_id=$2
              `,
              [context.user.id, args.id]
            );
            if (isUserAlreadySignedUp.length) {
              const role = isUserAlreadySignedUp[0].role;
              throw new UserInputError(`User is already enrolled signed up as a ${role}`);
            }
            
            await client.query(
              `
                INSERT INTO
                  course_instances(student_id, course_id, role)
                  VALUES ($1, $2, $3);
              `,
              [context.user.id, args.id, args.role]
            );

            const courses = await client.query(`
              SELECT *
                FROM courses
                WHERE id=$1
              `,
              [args.id],
            );
            return courses.rows[0];
          } catch (e) {
            log.debug('Error signing up for course', e);
          }
        },
      },
    };

    const server = new ApolloServer({
      context: async ({ req }) => {
        const token = req.headers.authorization || '';

        const { rows } = await client.query(`SELECT * FROM users WHERE id=$1`, [token]);

        return { user: rows[0] };
      },
      resolvers,
      typeDefs,
    });
    server.listen().then(({ url }) => {
      log.debug(`🚀  Server ready at ${url}`);
    });

    this.server = server;
  }
};

export default GraphQL;