import gql from 'graphql-tag';

const GET_COURSE = gql`
  query getCourseData($id: Int!) {
    course(id: $id) {
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

export default GET_COURSE;
