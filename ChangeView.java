import org.apache.pdfbox.pdmodel.*; //PDDocument;
//import org.apache.pdfbox.pdmodel.PageLayout;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;

//import javafx.print.PageLayout;

import java.io.File;
import java.io.IOException;

/**
 * change initial view to fit.
 *
 * @author J.P. McGlinn
 */
public final class ChangeView
{
    private ChangeView()
    {
        //utility class, should not be instantiated.
    }

    /**
     * This will print the documents data.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException
    {
        if( args.length != 2 )
        {
            usage();
        }
        else
        {
            PDDocument document = null;
            try
            {
                document = PDDocument.load( new File(args[0]) );
                if( document.isEncrypted() )
                {
                    throw new IOException( "Encrypted documents are not supported." );
                }

                // Set open action to FitDestination with first page from document
                PDPageFitDestination dest = new PDPageFitDestination();
                PDActionGoTo action = new PDActionGoTo();    
                dest.setPage(document.getDocumentCatalog().getPages().get(0));
                action.setDestination(dest);    
                document.getDocumentCatalog().setOpenAction(action);

                // Set page layout SINGLE_PAGE
                document.getDocumentCatalog().setPageLayout(PageLayout.SINGLE_PAGE);
                
                document.save( args[1] );
            }
            finally
            {
                if( document != null )
                {
                    document.close();
                }
            }
        }
    }

    /**
     * This will print the usage for this document.
     */
    private static void usage()
    {
        System.err.println( "Usage: java " + ChangeView.class.getName() + " <input-pdf> <output-pdf>" );
    }
}
