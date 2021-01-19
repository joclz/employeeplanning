import {Component, Input, OnInit} from '@angular/core';
import {DeckungsbeitragJahrData} from "../../../models/deckungsbeitragJahr-data";

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

  fontColor = '#CCC';

  @Input()
  public deckungsbeitragJahr: DeckungsbeitragJahrData;

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    legend: {
      labels: {
        fontColor: this.fontColor
      }
    },
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero:true,
          fontColor: this.fontColor
        },
      }],
      xAxes: [{
        ticks: {
          fontColor: this.fontColor
        },
      }]
    }
  };

  public barChartLabels = ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'];
  public barChartType = 'bar';
  public barChartLegend = true;

  public barChartData = [
    {data: [65, 59, 80, 85, 25, 55, 100, 80, 70, 60, 50, 90], label: 'Deckungsbeitrag'},
  ];

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    this.barChartData = [
      {
        data: [Number(this.deckungsbeitragJahr.januar),
          Number(this.deckungsbeitragJahr.februar),
          Number(this.deckungsbeitragJahr.maerz),
          Number(this.deckungsbeitragJahr.april),
          Number(this.deckungsbeitragJahr.mai),
          Number(this.deckungsbeitragJahr.juni),
          Number(this.deckungsbeitragJahr.juli),
          Number(this.deckungsbeitragJahr.august),
          Number(this.deckungsbeitragJahr.september),
          Number(this.deckungsbeitragJahr.oktober),
          Number(this.deckungsbeitragJahr.november),
          Number(this.deckungsbeitragJahr.dezember)],
        label: 'Deckungsbeitrag'
      },
    ];
  }
}
