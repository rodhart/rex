#!/usr/local/bin/perl

# Perl script to do automatic testing and coverage analysis of REX project

$home = "/usa/siegel/tmp";
$ant = "/usr/local/ant/bin/ant";
$svn = "/usr/local/bin/svn";

$out = "$home/build.out";
$err = "$home/build.err";

$maindir = "rex-test";

system("date >$out");
system("echo \"\n\" >>$out");

chdir($home) || die "Could not chdir $home";
if (-e $maindir) {
    system("rm -rf $maindir");
}
$cmd = "$svn checkout file:///home/www/repos/cisc475/trunk $maindir";
system("$cmd >>$out 2>$err");
chdir("$home/$maindir") || die "Could not chdir $home/maindir";
system("cp build-configs/build.properties-cisc475 build.properties");

system("$ant test >>$out 2>>$err");
system("rm -rf /home/www/cisc475/htdocs/junit/*; cp -r junit/reports /home/www/cisc475/htdocs/junit");

system("$ant javadoc >>$out 2>>$err");
system("rm -rf /home/www/cisc475/htdocs/api/*; cp -r doc/javadoc/* /home/www/cisc475/htdocs/api");

system("$ant cobertura >>$out 2>>$err");
system("rm -rf /home/www/cisc475/htdocs/cobertura/*; cp -r cobertura/reports /home/www/cisc475/htdocs/cobertura");

system("cp $out /home/www/cisc475/htdocs/build.out");
system("cp $err /home/www/cisc475/htdocs/build.err");

1;
