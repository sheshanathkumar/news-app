import { Injectable } from '@angular/core';
import { HomeComponent } from '../component/home/home.component';

import {Subject, Observable} from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private subject = new Subject<any>();
  newsCategory: string = "";

  constructor( ) { }

  setCategory(data: string) {
    console.log( "category in shared-service ", data)
    this.newsCategory = data;
    this.subject.next(data);
  }

  getCategory ( ) : Observable<any> {
    return this.subject.asObservable();
  }




}
