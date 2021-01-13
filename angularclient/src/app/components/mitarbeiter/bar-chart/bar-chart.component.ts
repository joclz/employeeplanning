import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };

  public barChartLabels = ['Jan', 'Feb', 'Mär', 'Apr','Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'];
  public barChartType =  'bar';
  public barChartLegend = true;

  public barChartData = [
  {data: [65, 59, 80, 85, 25, 55, 100, 80, 70, 60, 50, 90], label: 'Deckungsbeitrag'},
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
