// 引入插件
const {CleanWebpackPlugin} = require('clean-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
// 引入一个包
const path = require('path')

const isProd = process.env.NODE_ENV === 'production' // 是否生产环境

// 指定打包文件所在目录函数
function resolve (dir) {
  //return path.resolve(__dirname, dir)
  return path.resolve(__dirname, '..', dir)  
}

// webpack中的所有的配置信息都应该写在module.exports中
module.exports = {
  mode: isProd ? 'production' : 'development',
  //入口文件
  entry: {
    app: './src/main.ts'   
  },
  // 指定打包文件所在目录
  output: {
    path: resolve('dist'),
    filename: '[name].[contenthash:8].js'
  },
  //指定webpack打包时要使用模块
  module: {
    //指定要加载的规则
    rules: [
      {
        //test指定的是规则生效的文件
        test: /\.tsx?$/,
        use: 'ts-loader',
        include: [resolve('src')],
        //要排除的文件
      //  exclude: /node-modules/
      },
      //设置Less,css文件的处理
      {
        test: /\.less$/,
        use: [
          "style-loader",
          "css-loader",
          //引入postcss
          {
            loader: "postcss-loader",
            options: {
              postcssOptions:{
                plugins: [
                  [
                    "postcss-preset-env",
                    {
                      browsers: 'last 2 versions'
                    }
                  ]
                ]  
              }
            }
          },
          "less-loader"
        ]
      }
    ]
  },
  //配置Webpack插件
  plugins: [
    new CleanWebpackPlugin({
    }),

    new HtmlWebpackPlugin({
      template: './public/index.html'  //html页面目录
    })
  ],

  resolve: {
    extensions: ['.ts', '.tsx', '.js']
  },

  // devtool: isProd ? 'cheap-module-source-map' : 'cheap-module-eval-source-map',
  devtool: isProd ? 'source-map' : 'eval-source-map',

  devServer: {
    host: 'localhost', // 主机名
    // stats: 'errors-only', // 打包日志输出输出错误信息
    port: 8081,
    open: true,
    // static: resolve('dist') // 开发服务器启动路径
  },
}