import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { map } from 'rxjs/operators'
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products' //??size=100;spring data REST default 20
  private categoryUrl = 'http://localhost:8080/api/product-category' //??size=100;spring data REST default 20
  constructor(private httpClient: HttpClient) { }
  getProductList(theCategoryId: number): Observable<Product[]> {
    const searchUrl = `${this.baseUrl}/search/findByCategoryId?id=${theCategoryId}`;
    return this.getProducts(searchUrl)
  }
  searchProducts(theKeyWord: string | null): Observable<Product[]> {
    const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${theKeyWord}`;
    return this.getProducts(searchUrl)
  }

  private getProducts(searchUrl: string): Observable<Product[]> {
    return this.httpClient.get<GetResponseProducts>(searchUrl).pipe(
      map(response => response._embedded.products)
    );
  }

  getProductCategories(): Observable<ProductCategory[]>{
    return this.httpClient.get<GetResponseProductCategory>(this.categoryUrl).pipe(
      map(response => response._embedded.productCategory)
    )
  }


}

interface GetResponseProducts {
  // unwraps REST response to get Product array
  _embedded: {
    products: Product[]
  }
}

interface GetResponseProductCategory {
  // unwraps REST response to get Product array
  _embedded: {
    productCategory: ProductCategory[]
  }
}
/*
{
  "_embedded" : {
    "products" : [ {
      "sku" : "BOOK-TECH-1000",
      "name" : "JavaScript - The Fun Parts",
      "description" : "Learn JavaScript",
      "unitPrice" : 19.99,
      "imageUrl" : "assets/images/products/placeholder.png",
      "active" : true,
      "unitsInStock" : 100,
      "dateCreated" : "2023-06-18T16:47:20.000+00:00",
      "lastUpdated" : null,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/products/1"
        },
        "product" : {
          "href" : "http://localhost:8080/api/products/1"
        },
        "category" : {
          "href" : "http://localhost:8080/api/products/1/category"
        }
      }
    }
    */