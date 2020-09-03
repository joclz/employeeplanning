import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TableVertriebComponent} from './table-vertrieb.component';

describe('TableVertriebComponent', () => {
  let component: TableVertriebComponent;
  let fixture: ComponentFixture<TableVertriebComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableVertriebComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableVertriebComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
