#!/usr/bin/perl -w


my $topics = 10;
my $problems = 100;
my $figures = 10;
my $blocks = 10;

#difficulty range
my $diffrange = 100;
my $diffmin = 1;


my $usedBlocks = 0;
my $usedFigures = 0;


print "Start generating the UEF file ...\n";
open(FILE, ">bigexam.tex");

print FILE "\\documentclass\[master\]\{exam\} \n \\begin\{document\}\n\n\n";

for (my $t = 0; $t <  $topics; $t++) {

    for (my $p = 0; $p < $problems ; $p++) {
        my $diff = int(rand($diffrange));
        my $randBlockFigure = rand(10);
        if ($randBlockFigure < 5) {
            print FILE "\\begin\{problem\}\{Topic$t\}\{$diff\}\n";
            print FILE "\tProblem $p of topic $t with difficulty $diff with no block and no figure\n";
        } elsif ($randBlockFigure >=5 && $randBlockFigure < 8) {
            my $fig = int(rand($figures)) + ($t * $figures);
            print FILE "\\begin\{problem\}\{Topic$t\}\{$diff\}\n";
            print FILE "\tProblem $p of topic $t with difficulty $diff with no block and reference figure fig:figure$fig ref is \\ref{fig:figure$fig}\n";
        } elsif ($randBlockFigure >=8) {
            my $blk = int(rand($blocks)) + ($t * $blocks);
            print FILE "\\begin\{problem\}[requires=blk:block$blk]\{Topic$t\}\{$diff\}\n";
            print FILE "\tProblem $p of topic $t with difficulty $diff with block reference blk:block$blk\n";
        }
        
        print FILE "\t\\begin\{answers\}\n";
        #generate random answers, random select if it is correct or fixed
        my $numcorrect = 0;
        my $numanswers = int(rand(4));
        $numanswers += 2; #to make the range from 1 to 5
        for (my $a=1; $a <= $numanswers; $a++) {    
            my $rand = rand(10);
            my $answer;
            if ($rand < 5) {
               $answer = "\t\t\\answer This is answer $a of Problem $p of topic $t \n";
            } elsif ($rand >= 5 && $rand < 7) {
                $answer = "\t\t\\answer\[fixed\] This is answer $a of Problem $p of topic $t and it is fixed\n";
            } elsif ($rand >= 7 && $rand < 8) {
                $answer = "\t\t\\answer\[correct\] This is answer $a of Problem $p of topic $t and it is correct\n";
                $numcorrect++;
            } else {
                 $answer = "\t\t\\answer\[fixed, correct\] This is answer $a of Problem $p of topic $t and it is fixed and correct\n";
                 $numcorrect++;
            }
            
            #make sure that at least one is correct answer
            if ($a == $numanswers  && $numcorrect == 0  ) {
                $answer = "\t\t\\answer\[correct\] This is answer $a of Problem $p of topic $t and it is correct\n";
                $numcorrect++;
            }
            
            print FILE "$answer";
        }
        print FILE "\t\\end\{answers\}\n";
        print FILE "\\end\{problem\}\n\n";
        
        my $randButFigureOrBlock = int(rand(3));
        if ( $randButFigureOrBlock  == 0) {
            #do nothing
        } elsif ( $randButFigureOrBlock  == 1 && $usedFigures < $figures) {
            #add figure
            my $fig = $usedFigures + ($t * $figures);
            print FILE "\n\n\\begin\{figure\}\n";
            print FILE "\t\\begin\{center\}\n";
            print FILE "\t\tThis is figure $fig \n";
            print FILE "\t\t\\label\{fig\:figure$fig\}\n";
            print FILE "\t\\end\{center\}\n";
            print FILE "\\end\{figure\}\n\n";
            $usedFigures++;
        } elsif ( $randButFigureOrBlock  == 2 && $usedBlocks < $blocks) {
            # add block
            my $blk = $usedBlocks + ($t * $blocks);
            print FILE "\n\n\\begin\{block\}\{blk\:block$blk\}\n";
            print FILE "This is block $blk\n";
            print FILE "\\end\{block\}\n\n\n";
            $usedBlocks++;
        }
    }
    #make sure to print all blocks and figures
    while ($usedBlocks < $blocks) {
        my $blk = $usedBlocks + ($t * $blocks);
        print FILE "\n\n\\begin\{block\}\{blk\:block$blk\}\n";
        print FILE "This is block $blk\n";
        print FILE "\\end\{block\}\n\n\n";
        $usedBlocks++;
    }

    if ( $usedFigures < $figures) {
        my $fig = $usedFigures + ($t * $figures);
        print FILE "\n\n\\begin\{figure\}\n";
        print FILE "\t\\begin\{center\}\n";
        print FILE "\t\tThis is figure $fig \n";
        print FILE "\t\t\\label\{fig\:figure$fig\}\n";
        print FILE "\t\\end\{center\}\n";
        print FILE "\\end\{figure\}\n\n";
        $usedFigures++;
    }

    $usedBlocks = 0;
    $usedFigures = 0;
}




#print goodbye block
print FILE "\n\n\\begin\{block\}\{block\:GoodbyeMessage}\n";
print FILE "\tThis is goodbye block\n";
print FILE "\\end\{block\}\n\n\n";
    
print FILE "\n\n\\end\{document\}";
close(FILE);

print "Finish generating the UEF file.\n";
print "Start generating the ECF file ...\n";
open(FILE, ">bigexam.ecf");
for (my $t = 0; $t <  $topics; $t++) {
    print FILE "include $problems problems on \"Topic$t\" with difficulty in (-\\infty, \\infty) at 2 points;\n"
}

print FILE "\nappend block:GoodbyeMessage;\n\n";

print FILE 'versions are "version0", "version1", "version2", "version3", "version4" ,"version5", "version6", "version7", "version8", "version9";';
close(FILE);
print "FINISH generating the ECF file\n";
