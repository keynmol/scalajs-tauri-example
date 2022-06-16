# scalajs-tauri-example

This is a very small of using [Tauri](https://tauri.studio) to develop a desktop application with Scala.js frontend.

1. Uses [Laminar](https://laminar.dev)
2. Bundling done with [Vite.js](https://vitejs.dev)
3. [ScalablyTyped](https://scalablytyped.org/docs/readme.html) provides facades to access Tauri's APIs (for things like windows titles and system dialogs)

## Run

1. Do all the setup necessary to run Tauri
2. Run `sbt buildFrontend` to build Scala.js frontend
3. run `npm run tauri dev` to launch the app
4. Done.

![2022-06-16 15 43 12](https://user-images.githubusercontent.com/1052965/174096023-0bbed885-6fa3-468d-a7ed-4a6143e7c821.gif)
