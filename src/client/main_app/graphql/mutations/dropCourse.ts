import gql from 'graphql-tag';

const dropCourse = gql`
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

export default dropCourse;
