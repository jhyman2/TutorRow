import gql from 'graphql-tag';

const GET_USER_UNIVERSITY = gql`
  {
    user {
      id
      full_name
      university {
        id
        name
      }
    }
  }
`;

export default GET_USER_UNIVERSITY;
