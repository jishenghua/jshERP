const path = require('path')
const CompressionPlugin = require('compression-webpack-plugin')

function resolve (dir) {
    return path.join(__dirname, dir)
}

let objectProject = {
    index: {
        entry: 'src/main.js', // page 的入口
        template: 'public/index.html', // 模板来源
        filename: 'index.html', // 在 dist/index.html 的输出
        // 当使用 title 选项时，template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
        title: 'Index Page',
        // 在这个页面中包含的块，默认情况下会包含,提取出来的通用 chunk 和 vendor chunk。
        chunks: ['chunk-vendors', 'chunk-common', 'index']
    }
}
let page = {}
let projectname = process.argv[3] // 获取执行哪个文件
if (process.env.NODE_ENV === 'development') {
    page = objectProject
} else {
    page[projectname] = objectProject[projectname]
}
let publicPathStr, dirStr
if (projectname === undefined || projectname === 'index') {
    publicPathStr = '/'
    dirStr = 'dist'
} else {
    publicPathStr = './'
    dirStr = 'dist/plugin/' + projectname
}

// vue.config.js
module.exports = {
    outputDir: dirStr, // 标识是打包哪个文件
    filenameHashing: true,
    pages: page,
    /*
      Vue-cli3:
      Crashed when using Webpack `import()` #2463
      https://github.com/vuejs/vue-cli/issues/2463
     */
    // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
    productionSourceMap: false,
    // 静态文件的路径前缀
    publicPath: publicPathStr,
    configureWebpack: config => {
    // 生产环境取消 console.log
        if (process.env.NODE_ENV === 'production') {
            config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
        }
    },
    chainWebpack: (config) => {
        config.resolve.alias
            .set('@$', resolve('src'))
            .set('@api', resolve('src/api'))
            .set('@assets', resolve('src/assets'))
            .set('@comp', resolve('src/components'))
            .set('@views', resolve('src/views'))
            .set('@layout', resolve('src/layout'))
            .set('@static', resolve('src/static'))
            .set('@mobile', resolve('src/modules/mobile'))
        // 生产环境，开启js\css压缩
        if (process.env.NODE_ENV === 'production') {
            config.plugin('compressionPlugin').use(new CompressionPlugin({
                test: /\.(js|css|less)$/, // 匹配文件名
                threshold: 10240, // 对超过10k的数据压缩
                deleteOriginalAssets: false // 删除源文件
            }))
        }
        // 配置 webpack 识别 markdown 为普通的文件
        // config.module
        //     .rule('markdown')
        //     .test(/\.md$/)
        //     .use()
        //     .loader('file-loader')
        //     .end()
    },
    css: {
        loaderOptions: {
            less: {
                modifyVars: {
                    /* less 变量覆盖，用于自定义 ant design 主题 */
                    'primary-color': '#1890FF',
                    'link-color': '#1890FF',
                    'border-radius-base': '4px'
                },
                javascriptEnabled: true
            }
        }
    },
    devServer: {
        port: 3000,
        proxy: {
            '/jshERP-boot': {
                target: 'http://localhost:8888', // 请求本地 需要jshERP-boot后台项目
                ws: false,
                changeOrigin: true
            }
        }
    },
    lintOnSave: undefined
}
