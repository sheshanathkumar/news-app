import { Component } from '@angular/core';
import { SharedService } from 'src/app/service/shared.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  category = "";

  constructor(private shared : SharedService) {

  }

  getCategoryValue ( category : string) {
    console.log(category)
    this.category = category;
    this.shared.setCategory(category);
  }

 

}
