import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComingEventsComponent } from './coming-events.component';

describe('ComingEventsComponent', () => {
  let component: ComingEventsComponent;
  let fixture: ComponentFixture<ComingEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComingEventsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComingEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
