import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OrderInPreparationComponent} from './order-in-preparation.component';

describe('OrderInPreparationComponent', () => {
  let component: OrderInPreparationComponent;
  let fixture: ComponentFixture<OrderInPreparationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderInPreparationComponent]
    });
    fixture = TestBed.createComponent(OrderInPreparationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
