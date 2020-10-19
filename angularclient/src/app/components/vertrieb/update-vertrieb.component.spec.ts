import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateVertriebComponent} from './update-vertrieb.component';

describe('UpdateVertriebComponent', () => {
  let component: UpdateVertriebComponent;
  let fixture: ComponentFixture<UpdateVertriebComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateVertriebComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateVertriebComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
 //   expect(component).toBeTruthy();
 // });
});
