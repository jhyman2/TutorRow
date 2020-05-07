import gql from 'graphql-tag';

const getUniversityCourses = gql`
  query getUniversityCourses($id: Int!) {
    university(id: $id) {
      name
      id
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
`;

export default getUniversityCourses;
