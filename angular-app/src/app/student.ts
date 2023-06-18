class Student {
    id: number;
    name: string
    age: number

    constructor(id: number, name: string, age: number){
        this.id = id;
        this.name = name;
        this.age = age
    }
}

let studentsArray: Student[] = [
    new Student(1, "Foo", 10),
    new Student(2, "Bar", 11),
    new Student(1, "Baz", 12),
]

export {
    Student,
    studentsArray
}