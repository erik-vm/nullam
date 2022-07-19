import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpService } from '../http.service';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {

  isEditing = false;

  addEventForm: FormGroup = this.fb.group({
    id: null,
    name: [null, [Validators.required]],
    time: [null, [Validators.required]],
    location: [null, [Validators.required]],
    description: [null, [Validators.maxLength(1000)]],
    totalParticipants: null,
    personParticipants: null,
    companyParticipants: null
  });


  constructor(
    private router: Router,
    private fb: FormBuilder,
    private httpService: HttpService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      if (params.id) {
        this.httpService.findEventById(params.id).subscribe((data: any) => {
          this.addEventForm.patchValue(data);
        });
      }
    });
  }

  addEvent(){
    if(this.addEventForm.valid){
if(this.isEditing){
  this.httpService.updateEventById( ,this.addEventForm.getRawValue()).subscribe(()=>{
    this.addEventForm.reset;
    this.router.navigate('/home')
  })

}else{
  this.httpService.addEvent(this.addEventForm.getRawValue()).subscribe(()=>{
    this.router.navigate(['/home'])})
}
    }    
  }
}
