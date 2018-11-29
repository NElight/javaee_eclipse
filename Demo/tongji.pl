#!/usr/bin/perl


#$infile = shift;
$infile = "C:\\Users\\Administrator.USER-20180325TR\\Desktop\\text.xls";
print $infile . "\n";

@yuanlingdao=qw(陆一凡
牛步云
张秋红
寇文斌
王培军
);

@jianweihui=qw(时威锋
钟凌
);

@fukeji=qw(王宇辉
杨建杰
);
@jianwubaozhang=qw(李成君
布庆辉
郑尧坤
闫明哲
薛丽丽
张浩
刘盼盼
张黎晴
吴晓峥
禹恒
杨洁
胡宗普
蔡瑞杰
马雨
);
@zhenggongtuandui=qw(王健
乔翠莹
陈斐
周静
魏诗桐
韩毅
蒋艺蕾
赵轩
);
@xingjiantuandui=qw(朱蕾
王哲
梁祖豪
闫汾
魏利梅
赵莎莎
李航
马明浩
李京辑
王永军
肖华军
张百惠
杨超凡
左文
);
@susongjiandu=qw(陈耀武
亢华夏
冉洁
陈炫光
左玲
丁宇
买庆娟
);
@minhangtuandui=qw(陶玉堂
张丽
柴治中
王坤
);



$renyuan = {
	"院领导"=>\@yuanlingdao,
	"检委会专职委员"=>\@jianweihui,
	"副科级领导"=>\@fukeji,
	"检务保障团队"=>\@jianwubaozhang,
	"政工团队"=>\@zhenggongtuandui,
	"刑检团队"=>\@xingjiantuandui,
	"诉讼监督团队"=>\@susongjiandu,
	"民行团队"=>\@minhangtuandui,
};

@bumenshunxu = ("院领导",
	"检委会专职委员",
	"副科级领导",
	"检务保障团队",
	"政工团队",
	"刑检团队",
	"诉讼监督团队",
	"民行团队");


$rel = {};
$total_key = "total";
$am_daka_key = "am_daka";
$pm_daka_key = "pm_daka";
$am_chidao_key = "am_chidao";
$pm_zaotui_key = "pm_zaotui";
$liwai_key = "liwai";

open IN, "<", $infile;
<IN>;

while(<IN>){
	
	chomp;
	my @contents = split ("\t");
	my $p_name = $contents[0];
	my $am_pm = $contents[2];
	
	
	$rel -> {$p_name} -> {$am_pm} -> {$total_key} ++;
	
	if ($contents[5] || ($contents[10] && $contents[11])){
		$rel -> {$p_name} -> {$am_pm} -> {$am_daka_key} ++;
	}
	
	if ($contents[6] || ($contents[10] && $contents[11])){
		$rel -> {$p_name} -> {$am_pm} -> {$pm_daka_key} ++;
	}
	
	if ($contents[7]){
		$rel -> {$p_name} -> {$am_pm} -> {$am_chidao_key} ++;
	}
	
	if ($contents[8]){
		$rel -> {$p_name} -> {$am_pm} -> {$pm_zaotui_key} ++;
	}
	
	if ($contents[10] && $contents[11]){
		$rel -> {$p_name} -> {$am_pm} -> {$liwai_key} ++;
	}
}

print "姓名" . "\t" . "早上应打卡总次数" . "\t" . "早上实际打卡次数" . "\t" . "迟到次数" . "\t" . "下午应打卡总次数" . "\t" . "下午实际打卡次数" . "\t"
. "早退次数" . "\t" . "早上打卡率(%)" . "\t" . "下午打卡率(%)" . "\t" . "迟到率(%) " . "\t" . "早退率(%)" . "\t" . "早上例外次数" . "\t" . "下午例外次数" . "\n";
#foreach (keys %{$rel}){
#	my $zaoshang_total = $rel->{$_} -> {"上午"} -> {$total_key} || 0;
#	my $zaoshang_daka = $rel -> {$_} -> {"上午"} -> {$am_daka_key} || 0;
#	my $zaoshang_chidao = $rel -> {$_} -> {"上午"} -> {$am_chidao_key} || 0;
#	my $xiawu_total = $rel->{$_} -> {"下午"} -> {$total_key} || 0;
#	my $xiawu_daka = $rel -> {$_} -> {"下午"} -> {$pm_daka_key} || 0;
#	my $xiawu_zaotui = $rel -> {$_} -> {"下午"} -> {$pm_zaotui_key} || 0;
#	print $_ . "\t" . $zaoshang_total . "\t" . $zaoshang_daka . "\t" . $zaoshang_chidao . "\t"
#	. $xiawu_total . "\t" . $xiawu_daka . "\t" . $xiawu_zaotui . "\t" . $zaoshang_daka / $zaoshang_total * 100 . "\t"
#	. $xiawu_daka / $xiawu_total * 100 . "\t" . $zaoshang_chidao / $zaoshang_total * 100 . "\t" 
#	. $xiawu_zaotui / $xiawu_total * 100 . "\n";
#}

$bumen_tongji = {};

foreach my $bumen (@bumenshunxu){
	if ($bumen ne '检务保障团队'){
		next;
	}
	my @temp_renyuan = @{$renyuan->{$bumen}};
	
	my $lower_level_am = 0;
	my $meddle_level_am = 0;
	my $high_level_am = 0;
	my $lower_level_pm = 0;
	my $meddle_level_pm = 0;
	my $high_level_pm = 0;
	my $total_person = 0;
	
	foreach (@temp_renyuan){
		my $zaoshang_total = $rel->{$_} -> {"上午"} -> {$total_key} || 0;
		my $zaoshang_daka = $rel -> {$_} -> {"上午"} -> {$am_daka_key} || 0;
		my $zaoshang_chidao = $rel -> {$_} -> {"上午"} -> {$am_chidao_key} || 0;
		my $zaoshang_liwai = $rel -> {$_} -> {"上午"} -> {$liwai_key} || 0;
		my $xiawu_total = $rel->{$_} -> {"下午"} -> {$total_key} || 0;
		my $xiawu_daka = $rel -> {$_} -> {"下午"} -> {$pm_daka_key} || 0;
		my $xiawu_zaotui = $rel -> {$_} -> {"下午"} -> {$pm_zaotui_key} || 0;
		my $xiawu_liwai = $rel -> {$_} -> {"下午"} -> {$liwai_key} || 0;
		my $zaoshang_daka_lv = $zaoshang_daka / $zaoshang_total * 100;
		my $xiawu_daka_lv = $xiawu_daka / $xiawu_total * 100;
		my $zaoshang_chidao_lv = $zaoshang_chidao / $zaoshang_total * 100;
		my $xiawu_zaotui_lv = $xiawu_zaotui / $xiawu_total * 100;
		print $_ . "\t" . $zaoshang_total . "\t" . ($zaoshang_daka - $zaoshang_liwai) . "\t" . $zaoshang_chidao . "\t"
		. $xiawu_total . "\t" . ($xiawu_daka - $xiawu_liwai) . "\t" . $xiawu_zaotui . "\t" . $zaoshang_daka_lv . "\t"
		. $xiawu_daka_lv . "\t" . $zaoshang_chidao_lv . "\t" 
		. $xiawu_zaotui_lv . "\t" . "$zaoshang_liwai" . "\t" . "$xiawu_liwai" . "\n";
		
		
		$total_person += 1;
		if ($zaoshang_daka_lv ge 80) {
			$high_level_am += 1;
		}elsif ($zaoshang_daka_lv lt 80 && $zaoshang_daka_lv ge 60){
			$meddle_level_am += 1;
		}else {
			$lower_level_am += 1;
		}
		
		if ($xiawu_daka_lv ge 80) {
			$high_level_pm += 1;
		}elsif ($xiawu_daka_lv lt 80 && $xiawu_daka_lv ge 60){
			$meddle_level_pm += 1;
		}else {
			$lower_level_pm += 1;
		}
		
	}
	
	my @temp_tongji_array = ($lower_level_am, $meddle_level_am, $high_level_am, $lower_level_pm, $meddle_level_pm, $high_level_pm);	
	my @temp_tongji_array1 = map {$_, $_ / $total_person * 100} @temp_tongji_array;
	
	$bumen_tongji -> {$bumen} = \@temp_tongji_array1;
}

print "\n\n";
print "\n\n";

print "部门名称" . "\t" ."人数" . "\t" . "上午打卡(<60%)"  . "\t".
"人数" . "\t" . "上午打卡(>60%,<80%)" . "\t". "人数" . "\t" . "上午打卡(>80%)" . "\t" .
"人数" . "\t" . "下午打卡(<60%)"  . "\t". "人数" . "\t" ."下午打卡(>60%,<80%)" . "\t". "人数" . "\t" . "下午打卡(>80%)". "\n";
foreach my $bumen (@bumenshunxu){
	if ($bumen ne '检务保障团队'){
		next;
	}
	my @temp = @{$bumen_tongji -> {$bumen}};
	print $bumen . "\t";
	print join "\t", @temp;
	print "\n";
}



