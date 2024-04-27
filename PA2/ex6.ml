module F = Format

let sigma n m f =
  let rec my_sigma i =
    if i > m then 0
    else my_sigma (i + 1) + f i
  in my_sigma n

let _ =
  let _ = F.printf "%d\n" (sigma 10 10 (fun x -> x)) in 
  let _ = F.printf "%d\n" (sigma 11 10 (fun x -> x)) in 
  let _ = F.printf "%d\n" (sigma 10 5 (fun x -> x)) in 
  let _ = F.printf "%d\n" (sigma 1 10 (fun x -> if x mod 2 = 0 then 1 else 0)) in 
  let _ = F.printf "%d\n" (sigma 2 10 (fun x -> x + 10)) in 
  let _ = F.printf "%d\n" (sigma 0 100 (fun x -> 0)) in 
  F.printf "%d\n" (sigma 10 12 (fun x -> 2 * x));;