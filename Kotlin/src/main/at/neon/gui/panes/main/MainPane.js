var vm = new Vue({
    el: '#app',
    data: {
        bridge: null,
        data: null,
        data2dr: null,
        data2dc: null,
        data2d: null,

        chartLayout: {
            datarevision: 0,
            autosize: true,
            title: 'Plotly.js chart',
            font: {
                size: 16
            },
            grid: {rows: 2, columns: 1, pattern: 'independent'}
        },
        chartConfig: {
            // https://plot.ly/javascript/responsive-fluid-layout/
            responsive: true
        }
    },
    computed: {
        datax: function () {
            var extracted = [];
            if (this.data) {
                var i;
                var size = this.data.size();
                for (i = 0; i < size; i++) {
                    extracted.push(this.data.get(i).getX())
                }
            }
            return extracted;
        },
        datay: function () {
            var extracted = [];
            if (this.data) {
                var i;
                var size = this.data.size();
                for (i = 0; i < size; i++) {
                    extracted.push(this.data.get(i).getY())
                }
            }
            return extracted;
        },
        data2djs: function () {
            var extracted = [];
            if (this.data2d) {
                var r;
                for (r = 0; r < this.data2dr; r++) {
                    extracted.push([]);
                    var c;
                    for (c = 0; c < this.data2dc; c++) {
                        extracted[r].push(this.data2d.get(r * this.data2dc + c))
                    }
                }
            }
            return extracted;
        },
        lineChartTrace: function () {
            return {
                x: this.datax,
                y: this.datay,
                mode: 'lines+markers',
                type: 'scatter',
                name: "Trace 'X VS Y'"
            }
        },
        heatMapChartTrace: function () {
            return {
                z: this.data2djs,
                type: 'heatmap',
                name: 'heat_map_data',
                xaxis: 'x2',
                yaxis: 'y2'
            }
        },
        traces: function () {
            return [this.lineChartTrace, this.heatMapChartTrace]
        }
    },
    mounted: function () {
        var thiz = this;
        this.$nextTick(function () {
            // Code that will run only after the
            // entire view has been rendered
            Plotly.newPlot('chart', thiz.traces, thiz.chartLayout, thiz.chartConfig);
        })
    },
    watch: {
        traces: {
            handler: function (oldVar, newVar) {
                this.refreshChart()
            },
            deep: true
        },
        layout: {
            handler: function (oldVar, newVar) {
                this.refreshChart()
            },
            deep: true
        }
    },
    methods: {
        refreshChart: function () {
            this.chartLayout.datarevision = this.traces.map(function (trace) {
                return trace.length
            }).reduce(function (prev, cur) {
                return prev + cur;
            }, 0);
            Plotly.react('chart', this.traces, this.chartLayout, this.chartConfig);
        }
    }
});