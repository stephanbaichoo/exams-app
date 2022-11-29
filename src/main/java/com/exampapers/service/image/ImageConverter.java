package com.exampapers.service.image;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.FileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageConverter {

    private static final String PATH = "C:\\Users\\steph\\Documents\\Projects\\ExamPapers\\src\\main\\resources\\";

    public void convertImageToSVG() throws IOException, TranscoderException {
/*        SVGTranscoder svgTranscoder = new SVGTranscoder();

        File file = new File(PATH.concat("Screenshot_20221104_105224.png"));
        BufferedReader reader =
                new BufferedReader(new FileReader(PATH.concat("Screenshot_20221104_105224.png")));
        TranscoderInput transcoderInput = new TranscoderInput(reader);

        OutputStream ostream = new FileOutputStream((PATH.concat("image.svg")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(PATH.concat("image.svg")));
        TranscoderOutput transcoderOutput = new TranscoderOutput(writer);


        svgTranscoder.transcode(transcoderInput, transcoderOutput);*/

        // Load source file PNG for conversion
        Converter converter = new Converter(PATH.concat("Screenshot_20221104_105224.png"));
// Prepare conversion options for target format SVG
        ConvertOptions convertOptions = FileType.fromExtension("svg").getConvertOptions();
// Convert to SVG format
        converter.convert(PATH.concat("image.svg"), convertOptions);

    }

}
