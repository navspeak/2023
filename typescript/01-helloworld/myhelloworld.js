// https://github.com/darbyluv2code/fullstack-angular-and-springboot/blob/master/install-angular-tools/ms-windows/install-ms-windows.md
/*
- tsc myhellowordld.ts => .js => node myhelloworld.js
*/
var firstName = "Nav";
var lastName = "Kumar";
console.log("hello world");
console.log("Hello ".concat(firstName, " ").concat(lastName));
// even on error tsc generates js. Use ts -noEmitOnError
var reviews = [5, 4, 6, 7];
for (var index = 0; index < reviews.length; index++) {
    console.log(reviews[index]);
}
var arrayGrowable = ["hhh", "jjj"];
arrayGrowable.push("kkk");
for (var _i = 0, arrayGrowable_1 = arrayGrowable; _i < arrayGrowable_1.length; _i++) {
    var iterator = arrayGrowable_1[_i];
    console.log(iterator);
}
