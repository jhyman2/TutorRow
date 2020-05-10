import gql from 'graphql-tag';

const ENROLL_STUDENT_IN_UNIVERSITY = gql`
  mutation enrollStudentInUniversity($id: Int!) {
    enrollStudentInUniversity(id: $id) {
      id
      university {
        id
        name
      }
    }
  }
`;

export default ENROLL_STUDENT_IN_UNIVERSITY;
