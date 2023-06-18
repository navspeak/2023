* NPM for typescript to javascript transpiler, angular cli, minification lib
* https://stackblitz.com/edit/angular-pkde7g?file=src%2Fmain.ts
* npm install -g @angular/cli
* cd to a directory & npm new <app-name>
    * creates a new workspace + skeleton
    * Reco: 1 project : 1 workspace
* [app](../app/) is a component
* webpack:
    * improves performance & load time
    * manages dependencies
    * combine all JS files & transforms to 1 big file, reducing server roundtrips required otherwise.
    * also eliminates unsused code
* ng serve --open
    * Launches the server webpack-dev-server, builds ur project, watches files
* JIT & AOT compiler
    * ngc = JIT: Inspect the source and go to vendor.js you will find compiler.js is delivered to the browser which will translate angular notations that browser will understand. You will also find that in main.js, you have the string interpolation syntax in some form. Happens when we load the application. Load slow. Used in dev, not prod. Less secure
    * ng serve --aot=true: Vendor.js - no compiler.js (not bundled and delivered to browser. Already taken place during ng serve). Size is less. Load fast as compiled during building. Secure. default starting angular 9
* bootstrap: npm install bootstrap and npm install jquery
    * add "node_modules/bootstrap/dist/css/bootstrap.css" in [angular.json](../../angular.json)

```json
             "styles": [
              "src/styles.css",
              "node_modules/bootstrap/dist/css/bootstrap.css"
            ],
            "scripts": [
              "node_modules/jquery/dist/jquery.min.js",
              "node_modules/bootstrap/dist/js/bootstrap.min.js"
            ]`

```
* Angular Components:
    - View  - html
    - Style - css
    - behavior - .ts
  - ng new - creates a root component "app". Loads other components
  - ng generate component students
  - [app.component.ts](./app.component.ts) has a selector of app-root that is refered in index.html
  - [student.component.ts](./student/student.component.ts) has a selector <app-students/> so we can add it to the view of root component i.e. [app.component.html](./app.component.html)

* Data Binding
 - One way betweem Model to View using {{ string interpolation }}
 - two way: input field ngModel
* Event Binding
 - button, option, search
* Property Binding & Data Exchange between components
 - ng g c student-age
 - [student-age](./student-age/student-age.component.ts) add @Input
* Services 
 - ng generate service student
* dependency injection
 - parameterized constructor
 - use Injector, @Injectable (StudentService) and provider in app.module.ts or in @Injectable Metadata



