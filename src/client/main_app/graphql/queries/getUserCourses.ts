import gql from 'graphql-tag';

const GET_USER_UNIVERSITY = gql`
  query getUserUniversity {
    user {
      id
      full_name
      university {
        id
        name
      }
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
    }
  }
`;

export default GET_USER_UNIVERSITY;
