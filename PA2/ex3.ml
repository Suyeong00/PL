module F = Format

let prime_list n = 
  let lst = [] in
  let rec is_prime t num = (*num이 소수인지 판단, idx < last*)
    if t = num then true
    else 
      if num mod t = 0 then false 
      else is_prime (t + 1) num 
  in
  let rec make_prime_lst i lst =
    if i > n then lst
    else 
      if is_prime 2 i then make_prime_lst (i + 1) (i :: lst)
      else make_prime_lst (i + 1) lst
  in
  make_prime_lst 2 lst

let rec count_factor i n result =
  if n mod i = 0 then count_factor i (n / i) (result + 1)
  else result

let factor_list n = 
  let rec factor_count num lst = 
    match lst with
    | [] -> []
    | h :: t -> 
      let cnt = count_factor h num 0 in
      if cnt > 0 then (h, cnt) :: factor_count num t
      else factor_count num t
    in 
    List.rev(factor_count n (prime_list n))

let print_tuple tp = 
  print_string "(";
  print_int (fst tp);
  print_string ", ";
  print_int (snd tp);
  print_string ")";;

let print_list lst =
  print_string "[";
  let rec print_elements lst =
    match lst with
    | [] -> ()
    | [x] -> print_tuple x
    | x :: xs -> print_tuple x; print_string "; "; print_elements xs
  in
  print_elements lst;
  print_string "]\n";; 

let _ =
  print_list (factor_list 10);
  print_list (factor_list 17);
  print_list (factor_list 27);
  print_list (factor_list 315);
  print_list (factor_list 777);
  print_list (factor_list 1024);