import gql from 'graphql-tag';

const SIGNUP_FOR_COURSE = gql`
  mutation signupForCourse($id: Int!, $role: String!) {
    signupForCourse(id: $id, role: $role) {
      id
      course_num
      department
      description
      name
      num_credits
      professor
      university_id
      students {
        id
        full_name
      }
      tutors {
        id
        full_name
      }
    }
  }
`;

export default SIGNUP_FOR_COURSE;
