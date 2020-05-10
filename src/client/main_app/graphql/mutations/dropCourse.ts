import gql from 'graphql-tag';

const DROP_COURSE = gql`
  mutation dropCourse($id: Int!) {
    dropCourse(id: $id) {
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

export default DROP_COURSE;
