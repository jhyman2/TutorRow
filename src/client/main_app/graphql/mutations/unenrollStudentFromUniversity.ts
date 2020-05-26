import gql from 'graphql-tag';

const UNENROLL_STUDENT_FROM_UNIVERSITY = gql`
  mutation unenrollStudentFromUniversity {
    unenrollStudentFromUniversity {
      id
      university {
        id
        name
      }
    }
  }
`;

export default UNENROLL_STUDENT_FROM_UNIVERSITY;
