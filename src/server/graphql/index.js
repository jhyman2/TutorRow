const { ApolloServer, gql } = require('apollo-server');
import log from 'loglevel';

class GraphQL {
  constructor (client) {
    const typeDefs = gql`
      type University {
        id: Int
        name: String
      }

      type Student {
        id: Int
        university: University
        full_name: String
        courses: [Course]
      }

      type Course {
        id: Int
        name: String
        department: String
        professor: String
        description: String
        num_credits: Int
        university_id: Int
      }

      # The "Query" type is special: it lists all of the available queries that
      # clients can execute, along with the return type for each. In this
      # case, the "books" query returns an array of zero or more Books (defined above).
      type Query {
        student(id: Int): [Student]
        universities: [University]
      }
    `; 

    const resolvers = {
      Query: {
        student: async (obj, args) => {
          try {
            const results = await client.query(`SELECT * FROM users WHERE id=$1`, [args.id]);
            return results.rows;
          } catch (e) {
            log.debug('Error resolving student', e);
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
      Student: {
        courses: async (student) => {
          try {
            const results = await client.query(`
              SELECT *
                  FROM course_instances
                  JOIN courses
                    ON course_instances.course_id=courses.id
                  WHERE student_id=$1`, [student.id]);
            return results.rows;
          }
          catch (e) {
            log.debug('Error resolving courses', e);
          }
        }
      }
    };

    const server = new ApolloServer({ typeDefs, resolvers });
    server.listen().then(({ url }) => {
      log.debug(`🚀  Server ready at ${url}`);
    });

    this.server = server;
  }
};

export default GraphQL;