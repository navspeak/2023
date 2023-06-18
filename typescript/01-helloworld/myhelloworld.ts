// https://github.com/darbyluv2code/fullstack-angular-and-springboot/blob/master/install-angular-tools/ms-windows/install-ms-windows.md
/*
- tsc myhellowordld.ts => .js => node myhelloworld.js
*/
let firstName: string = "Nav";
let lastName: string = "Kumar";

console.log("hello world")
console.log(`Hello ${firstName} ${lastName}`)
// even on error tsc generates js. Use ts -noEmitOnError

let reviews: number[] = [5,4,6,7];
for (let index = 0; index < reviews.length; index++) {
    console.log(reviews[index])
    
}

let arrayGrowable: string[] = ["hhh", "jjj"];
arrayGrowable.push("kkk");
for (const iterator of arrayGrowable)  {
    console.log(iterator)
    if (iterator == "jjj")
        console.log("---")
}