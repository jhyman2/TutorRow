import gql from 'graphql-tag';

const GET_UNIVERSITIES = gql`
  {
    universities {
      name
      id
    }
  }
`;

export default GET_UNIVERSITIES;
