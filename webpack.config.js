module.exports = {
  entry: {
    bundle: './src/server/views/components/login/browser.js',
    main: ['babel-polyfill', './src/client/main_app/browser.js']
  },
  resolve: {
    modules: [
      './src/client',
      './node_modules',
    ],
  },
  module: {
    rules: [
      {
        test: /\.(ts|js)x?$/,
        use: 'ts-loader',
        exclude: /node_modules/
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        use: [
          'file-loader'
        ]
      },
      {
        test: /\.css$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'style-loader',
          },
          {
            loader: 'css-loader',
            options: {
              importLoaders: 1,
            }
          },
          {
            loader: 'postcss-loader',
          }
        ]
      }
    ]
  },
  resolveLoader: {
    modules: ['./node_modules']
  },
  output: {
    path: __dirname + '/dist',
    filename: '[name].js'
  },
  devServer: {
    contentBase: './dist',
    color: true
  }
}