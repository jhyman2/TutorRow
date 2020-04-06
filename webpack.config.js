module.exports = {
  entry: {
    bundle: './src/server/views/components/login/browser.js',
    main: ['babel-polyfill', './src/client/main_app/browser.js']
  },
  module: {
    rules: [
      {
        test: /\.(ts|js)x?$/,
        use: 'ts-loader',
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: [
          'style-loader',
          'css-loader'
        ]
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        use: [
          'file-loader'
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