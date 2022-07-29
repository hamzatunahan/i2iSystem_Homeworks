#!/usr/bin/perl
use warnings;
use strict;

my $filename = 'results.txt';
open(TxtData, '<', $filename) or die $!;
open(PrimeResult,"+>primeResults.txt")or die $!;

while(<TxtData>){
      
   if ($_ =~ m[Not]){}
   else{
   my @newnumber = split('=', $_);
   print $_;
   print PrimeResult @newnumber[0];
   print PrimeResult "\n";
}
}
close(TxtData);
close(PrimeResult);
