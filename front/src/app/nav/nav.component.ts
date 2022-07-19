import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  navigation = [
    {label: 'Avaleht', path: '/home'}, 
    {label: 'Ürituse lisamine', path: '/add-event'}]

  constructor() { }

  ngOnInit(): void {
  }

}
