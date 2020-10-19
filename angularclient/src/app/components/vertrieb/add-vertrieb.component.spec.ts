import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddVertriebComponent} from './add-vertrieb.component';

describe('AddVertriebComponent', () => {
  let component: AddVertriebComponent;
  let fixture: ComponentFixture<AddVertriebComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddVertriebComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddVertriebComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //it('should create', () => {
 //   expect(component).toBeTruthy();
 // });
});
