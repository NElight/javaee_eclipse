package com.yzg.svgHandle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AddLegendTool addLegendTool = new AddLegendTool(args[0], args[1], args[2]);
        addLegendTool.addLegend();
    }
}
