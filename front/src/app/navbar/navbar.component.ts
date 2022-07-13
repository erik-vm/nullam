import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  navigation = [{label: 'avaleht', path: '/home'}, {label: 'ürituse lisamine', path: '/add-event'}];


  constructor() { }



  ngOnInit(): void {
  }

}
