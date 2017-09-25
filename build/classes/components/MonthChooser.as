package components
{
	import mx.controls.DateChooser;
    import mx.core.mx_internal;
    import mx.events.CalendarLayoutChangeEvent;
    import mx.events.DateChooserEvent;
    
    
    use namespace mx_internal;

    public class MonthChooser extends DateChooser
    {
        
        public function MonthChooser()
        {
            monthNames = [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ];
            showToday = false;
                        
        }
        
        override protected function createChildren():void
        {
            super.createChildren();
            dateGrid.addEventListener(DateChooserEvent.SCROLL,dateGrid_scrollHandler);
            showToday = false;
        }
        
         override protected function measure():void
        {
             super.measure();
             dateGrid.visible = false;
               measuredHeight = measuredHeight - dateGrid.getExplicitOrMeasuredHeight();
               showToday = false;
        }
        
        private function dateGrid_scrollHandler(event:DateChooserEvent):void
        {
            var month:int = event.currentTarget.displayedMonth;
            var year:int = event.currentTarget.displayedYear;
            
            selectedDate = new Date(year, month, 1);
            
            
            var e:CalendarLayoutChangeEvent = new CalendarLayoutChangeEvent(CalendarLayoutChangeEvent.CHANGE);
            e.newDate = selectedDate;
            showToday = false;                        
            dispatchEvent(e);            
            
        }
    
    }

}