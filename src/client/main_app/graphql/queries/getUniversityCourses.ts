import gql from 'graphql-tag';

const GET_UNIVERSITY_COURSES = gql`
  query getUniversityCourses {
    user {
      id
      full_name
      coursesStudenting {
        id
        course_num
        department
        description
        name
        num_credits
        professor
      }
      coursesTutoring {
        id
        course_num
        department
        description
        name
        num_credits
        professor
      }
      university {
        id
        name
        courses {
          id
          course_num
          department
          description
          name
          num_credits
          professor
          university_id
        }
      }
    }
  }
`;

export default GET_UNIVERSITY_COURSES;
