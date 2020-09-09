import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateEinsatzComponent} from './update-einsatz.component';

describe('UpdateEinsatzComponent', () => {
  let component: UpdateEinsatzComponent;
  let fixture: ComponentFixture<UpdateEinsatzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateEinsatzComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateEinsatzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
