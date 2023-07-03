import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  currentCategoryId: number = 1;
  searchMode: boolean = false;
  constructor(private productService: ProductService,
    private route: ActivatedRoute) { }

  ngOnInit(): void { // similar to post construct
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    })
  }

  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    if (this.searchMode) {
      this.handleSearchProducts()
    } else {
      this.handleListProduct()
    }
  }
  handleSearchProducts() {
    const theKeyWord = this.route.snapshot.paramMap.get('keyword');
    this.productService.searchProducts(theKeyWord).subscribe(
      data => {
        this.products = data;
      }
    )
  }

  handleListProduct() {
    // check if id param is available
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    // this.<use activated route>.<snapshot at the moment>.<map of all route params>.<read id>
    if (hasCategoryId) {
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    } else {
      this.currentCategoryId = 1; //default
    }

    this.productService.getProductList(this.currentCategoryId).subscribe(
      data => {
        this.products = data;
      }
    )
  }
}
