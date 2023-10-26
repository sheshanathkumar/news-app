import { Component } from '@angular/core';
import { constant } from 'src/app/constant';
import { ApiService } from 'src/app/service/api-service.service';
import { SharedService } from 'src/app/service/shared.service';
import { environment } from 'src/environments/environment';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {

  categoryValue: Subscription;
  category = "all";
  articles: any = [];
  articlePage: any = [];
  newsApi = environment.api_url + constant.allNews;

  // pagination values
  record: number = 10;
  totalPage: number = 10;

  constructor(private apiService: ApiService, private shared: SharedService) {
    if (this.articles.lenght == 0 || this.category === "all")
      this.getAllNews(1);
    this.categoryValue = this.shared.getCategory().subscribe(() => {
      this.category = this.shared.newsCategory;
      console.log(this.category);
      this.showArticleByCategory(this.category);
    })

  }

  getAllNews(page: number) {

    if (this.articles.length !== 0) {
      this.getCurrentArticlePage(page);
    } else {
      this.apiService.get(this.newsApi).subscribe((data) => {
        console.log(data)
        this.articles = data;
        this.getCurrentArticlePage(1);
      }, (err) => {
        console.log("Error Occured")
      })
    }
  }

  getCurrentArticlePage(page: number) {
    let startIdx = (page - 1) * 10;
    let endIdx = ((startIdx + this.record) > this.articles.length) ? this.articles.length : startIdx + 9;
    this.getArticleRecord(this.articles, startIdx, endIdx, "all");
  }

  getArticleRecord(articles: any[], startIdx: number, endIdx: number, category: string) {
    this.articlePage = [];
    let cnt = 0;
    if (category === "all") {
      for (let i = startIdx; i < endIdx; i++) {
        this.articlePage.push(articles[i]);
        cnt++;
      }
    } else {
      for (let i = startIdx; i < endIdx; i++) {
        if (articles[i].category === category)
          this.articlePage.push(articles[i]);
        cnt++;
      }
    }



  }

  showArticleByCategory(category: string) {
    this.category = category;
    console.log("current category : ", this.category);

    this.getArticleRecord(this.articles, 0, this.articles.length, this.category);
  }

  readFullStory(url : string) {
    window.open(url, '_blank');
  }



}
