import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OrderDisplayComponent} from './order-display.component';

describe('OrderDisplayComponent', () => {
  let component: OrderDisplayComponent;
  let fixture: ComponentFixture<OrderDisplayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderDisplayComponent]
    });
    fixture = TestBed.createComponent(OrderDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
