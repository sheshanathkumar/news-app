import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http'
import { constant } from '../constant';
import { environment } from 'src/environments/environment';
import { map, catchError, finalize } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  currentUser = "Shesho"

  constructor(private http: HttpClient) {
  }

  private handleError(err: HttpErrorResponse) {

    return throwError(err);
  }


  handleResponse(res: HttpResponse<any>): any {
    console.log(res)
    return res.body;
  }

  get( url : string ) {
    console.log(url);
    return this.http.get<any>(url, {
      headers: {
        'Content-Type': 'application/json',
        'xuser': this.currentUser,
      }, observe: 'response'
    }).pipe(
      map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error => this.handleError(error))),
      finalize(() => { }));
  }


}
