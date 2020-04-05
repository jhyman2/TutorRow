const { ApolloServer, gql } = require('apollo-server');
import log from 'loglevel';

class GraphQL {
  constructor (client) {
    const typeDefs = gql`
      type University {
        id: Int
        name: String
      }

      type Student {
        id: Int
        university: [University]
        full_name: String
      }

      # The "Query" type is special: it lists all of the available queries that
      # clients can execute, along with the return type for each. In this
      # case, the "books" query returns an array of zero or more Books (defined above).
      type Query {
        student(id: Int): [Student]
      }
    `; 

    const resolvers = {
      Query: {
        student: (obj, args) => {
          return new Promise((resolve, reject) => {
            client.query(`SELECT * FROM users WHERE id=$1`, [args.id], (err, results) => {
              if (err) {
                reject(err);
              }
              resolve(results.rows);
            });
          });
        },
      },
    };

    const server = new ApolloServer({ typeDefs, resolvers });
    server.listen().then(({ url }) => {
      log.debug(`🚀  Server ready at ${url}`);
    });

    this.server = server;
  }
};

export default GraphQL;