import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TableEinsatzComponent} from './table-einsatz.component';

describe('TableEinsatzComponent', () => {
  let component: TableEinsatzComponent;
  let fixture: ComponentFixture<TableEinsatzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TableEinsatzComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableEinsatzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
