import { Component, OnInit } from '@angular/core';
import { SalesPerson } from './sales-person';

@Component({
  selector: 'app-sales-person-list',
  templateUrl: './sales-person-list-bootstrap.component.html',
  // templateUrl: './sales-person-list.component.html',
  styleUrls: ['./sales-person-list.component.css']
})
export class SalesPersonListComponent implements OnInit{
  salesPersonList: SalesPerson[] = [
    new SalesPerson("Anup", "Kumar", "anupkumar@xyz.com",5000),
    new SalesPerson("John", "Does", "johndoe@xyz.com",4000),
    new SalesPerson("Alice", "Wonderland", "alicewonderland@xyz.com",9000),
    new SalesPerson("Bob", "Viswas", "bobviswas@xyz.com",7000),
  ]
  constructor(){

  }
  ngOnInit(): void {
    
  }

}
