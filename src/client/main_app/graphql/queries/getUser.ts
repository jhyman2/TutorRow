import gql from 'graphql-tag';

const GET_USER = gql`
  query ($id: Int!) {
    student(id: $id) {
      courses {
        id
      }
      id
      full_name
      university {
        id
        name
      }
    }
  }
`;

export default GET_USER;
