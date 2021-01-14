import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: ['./doughnut-chart.component.css']
})
export class DoughnutChartComponent implements OnInit {

  fontColor = '#CCC';

  public  doughnutChartLabels = ['int. MA im Einsatz', 'ext. MA im Einsatz', 'int. MA nicht im Einsatz', 'ext. MA nicht im Einsatz'];
  public  doughnutChartData = [20, 25, 5, 3];
  public  doughnutChartType = 'doughnut';

  public doughnutChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    legend: {
      labels: {
        fontColor: this.fontColor
      }
    }
  };

  constructor() { }

  ngOnInit(): void {
  }

}
