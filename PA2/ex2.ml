module F = Format

let rec len lst =
  match lst with
  | [] -> 0
  | _ :: t ->
  1 + (len t)

let idx lst n = 
  let rec func i tlst =
    match tlst with
    | h :: t -> if i = n then h else func (i + 1) t
    | [] -> failwith "fail"
  in func 0 lst

let palindrome lst =
  let lst_len = len lst in
  let rec func i = 
    if i = lst_len / 2 then true
    else 
      if idx lst i = idx lst (lst_len - i - 1) then func (i + 1) 
      else false
    in func 0

let _ =
  let _ = F.printf "%b\n" (palindrome ["1";"2";"3";"4"]) in 
  let _ = F.printf "%b\n" (palindrome ["x";"m";"a";"s"]) in 
  let _ = F.printf "%b\n" (palindrome ["a";"m";"o";"r";"e";"r";"o";"m";"a"]) in 
  let _ = F.printf "%b\n" (palindrome ["1";"2";"3";"2";"1"]) in 
  F.printf "%b\n" (palindrome ["b";"o";"r";"r";"o";"w";"o";"r";"r";"o";"b"]);;